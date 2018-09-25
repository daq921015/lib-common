package build.dream.common;

import build.dream.common.annotations.Transient;
import build.dream.common.constants.Constants;
import build.dream.common.utils.NamingStrategyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by liuyandong on 2017/7/25.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        SpringApplication.run(Application.class, args);

        /*String packageName = "build.dream.common.erp.catering.domains";
        List<Class<?>> classes = obtainAllClass(packageName);
        for (Class<?> clazz : classes) {
            generateFieldNameInnerClassCode(clazz);
        }*/

//        test();
//        testSort();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(Application.class.getPackage().getName().replaceAll("\\.", "/"));
        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            System.out.println(url.toString());
        } else if ("jar".equals(protocol)) {
            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
            JarFile jarFile = jarURLConnection.getJarFile();

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                if (name.endsWith("Controller.class") && !name.contains("$")) {
                    String className = name.replaceAll("/", ".").substring(0, name.length() - 6);
                    Class<?> clazz = Class.forName(className);
                    Controller controller = AnnotationUtils.findAnnotation(clazz, Controller.class);
                    if (controller != null) {
                        RequestMapping requestMapping = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
                        if (requestMapping != null) {
                            System.out.println(StringUtils.join(requestMapping.value(), ","));
                        } else {

                        }
                    }
                }
            }
        }
    }

    public static List<Class<?>> obtainAllClass(String packageName) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(packageName.replaceAll("\\.", "/"));
        String path = url.getPath();
        File directory = new File(path);
        File[] files = directory.listFiles();

        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.contains("$")) {
                continue;
            }
            String className = packageName + "." + fileName.substring(0, fileName.length() - 6);
            classes.add(Class.forName(className));
        }
        return classes;
    }

    public static void printArray(int array[]) {
        int length = array.length;
        for (int index = 0; index < length; index++) {
            System.out.print(array[index]);
            if (index != length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    public static int[] buildRandomArray(int length, int maxValue) {
        int[] array = new int[length];
        for (int index = 0; index < length; index++) {
            array[index] = Double.valueOf(Math.random() * maxValue).intValue();
        }
        return array;
    }

    /**
     * 冒泡排序
     *
     * @param array
     */
    public static void bubbleSort(int array[]) {
        int length = array.length;
        for (int index = 0; index < length - 1; index++) {
            for (int innerIndex = 0; innerIndex < length - 1 - index; innerIndex++) {
                if (array[innerIndex] < array[innerIndex + 1]) {
                    int temp = array[innerIndex];
                    array[innerIndex] = array[innerIndex + 1];
                    array[innerIndex + 1] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     * 假设数组的长度为: length
     * 第一次循环，选出索引从 array[0]~array[length - 1] 中选出最大值或最小值与array[0]交换
     * 第二次循环，选出索引从 array[1]~array[length - 1] 中选出最大值或最小值与array[1]交换
     * 第三次循环，选出索引从 array[2]~array[length - 1] 中选出最大值或最小值与array[2]交换
     * ...
     *
     * @param array
     */
    public static void selectionSort(int array[]) {
        int length = array.length;
        for (int index = 0; index < length; index++) {
            int position = index;
            for (int innerIndex = index; innerIndex < length; innerIndex++) {
                if (array[innerIndex] < array[position]) {
                    position = innerIndex;
                }
            }

            if (index != position) {
                int temp = array[index];
                array[index] = array[position];
                array[position] = temp;
            }
        }
    }

    /**
     * 插入排序
     *
     * @param array
     */
    public static void insertionSort(int array[]) {
        int length = array.length;

        for (int index = 1; index < length; index++) {
            for (int innerIndex = index; innerIndex > 0 && array[innerIndex] > array[innerIndex - 1]; innerIndex--) {
                int temp = array[innerIndex];
                array[innerIndex] = array[innerIndex - 1];
                array[innerIndex - 1] = temp;
            }
        }
    }

    private static String obtainFileContent(File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

        char[] buffer = new char[1024];
        int length = -1;
        while ((length = inputStreamReader.read(buffer, 0, 1024)) != -1) {
            stringBuilder.append(buffer, 0, length);
        }

        inputStreamReader.close();
        inputStream.close();
        return stringBuilder.toString();
    }

    /**
     * 生成列名常量类代码
     *
     * @param domainClass
     * @throws IOException
     */
    private static void generateFieldNameInnerClassCode(Class<?> domainClass) throws IOException {
        // 生成列名常量类代码
        StringBuilder fieldNameInnerClassCode = new StringBuilder("public static final class FieldName extends BasicDomain.FieldName {");
        Field[] fields = domainClass.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isNative(modifiers)) {
                continue;
            }

            if (field.getAnnotation(Transient.class) != null) {
                continue;
            }

            String fieldName = field.getName();
            String columnName = NamingStrategyUtils.camelCaseToUnderscore(field.getName());
            fieldNameInnerClassCode.append("public static final String ").append(columnName.toUpperCase()).append(" = ").append("\"").append(fieldName).append("\";");
        }
        fieldNameInnerClassCode.append("}");
        writeCode(domainClass, fieldNameInnerClassCode.toString());
    }

    /**
     * 生成列名常量类代码
     *
     * @param domainClass
     * @throws IOException
     */
    public static void generateColumnNameInnerClassCode(Class<?> domainClass) throws IOException {
        StringBuilder columnNameInnerClassCode = new StringBuilder("public static final class ColumnName extends BasicDomain.ColumnName {");
        Field[] fields = domainClass.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isNative(modifiers)) {
                continue;
            }

            if (field.getAnnotation(Transient.class) != null) {
                continue;
            }

            String fieldName = field.getName();
            String columnName = NamingStrategyUtils.camelCaseToUnderscore(field.getName());
            columnNameInnerClassCode.append("public static final String ").append(columnName.toUpperCase()).append(" = ").append("\"").append(columnName).append("\";");
        }
        columnNameInnerClassCode.append("}");
        writeCode(domainClass, columnNameInnerClassCode.toString());
    }

    /**
     * 生成建造者模式代码
     *
     * @param domainClass
     */
    public static void generateBuildPatternCode(Class<?> domainClass) throws IOException {
        Class<?> cloneDomainClass = domainClass;
        String simpleName = domainClass.getSimpleName();
        StringBuilder code = new StringBuilder("public static class Builder {private final " + simpleName + " instance = new " + simpleName + "();");
        while (domainClass != Object.class) {
            Field[] fields = domainClass.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                code.append("public Builder " + name + "(" + field.getType().getSimpleName() + " " + name + ") { instance.set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "(" + name + ");return this;}");
            }
            domainClass = domainClass.getSuperclass();
        }
        code.append("public " + simpleName + " build() {return instance;}");
        code.append("}");
        code.append("public static Builder builder() {return new Builder();}");
        writeCode(cloneDomainClass, code.toString());
    }

    public static void writeCode(Class<?> domainClass, String code) throws IOException {
        String sourcePath = "E:\\huaneng-workspace\\saas-common\\src\\main\\java\\" + domainClass.getName().replaceAll("\\.", "\\\\") + ".java";
        File file = new File(sourcePath);
        String fileContent = obtainFileContent(file);
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.write(fileContent.substring(0, fileContent.lastIndexOf("}")));
        printWriter.println(code);
        printWriter.println("}");
        printWriter.flush();
        printWriter.close();
        fileWriter.close();
    }

    private static void test() {
        BigDecimal sunQuality = BigDecimal.valueOf(Double.valueOf(1.9891)).multiply(Constants.BIG_DECIMAL_TEN.pow(30));
        System.out.println("太阳质量为：" + sunQuality + "kg");

        BigDecimal earthQuality = BigDecimal.valueOf(Double.valueOf(5.965)).multiply(Constants.BIG_DECIMAL_TEN.pow(24));
        System.out.println("地球质量为：" + earthQuality + "kg");

        System.out.println("太阳的质量为地球质量的" + sunQuality.divide(earthQuality, 10, BigDecimal.ROUND_DOWN) + "倍");

        BigDecimal sunRadius = BigDecimal.valueOf(6.955).multiply(BigDecimal.TEN.pow(8));
        System.out.println("太阳的半径为：" + sunRadius + "m");

        BigDecimal sunVolume = Constants.BIG_DECIMAL_FOUR.multiply(Constants.BIG_DECIMAL_PI).multiply(sunRadius.pow(3)).divide(Constants.BIG_DECIMAL_THREE, 10, BigDecimal.ROUND_DOWN);
        System.out.println("太阳的体积为：" + sunVolume + "m³");

        BigDecimal earthRadius = BigDecimal.valueOf(6371000);
        System.out.println("地球的半径为：" + earthRadius + "m");
        BigDecimal earthVolume = Constants.BIG_DECIMAL_FOUR.multiply(Constants.BIG_DECIMAL_PI).multiply(earthRadius.pow(3)).divide(Constants.BIG_DECIMAL_THREE, 10, BigDecimal.ROUND_DOWN);
        System.out.println("地球的体积为：" + earthVolume + "m³");

        System.out.println("太阳的体积是地球体积的:" + sunVolume.divide(earthVolume, 10, BigDecimal.ROUND_DOWN) + "倍");

        BigDecimal moonQuality = BigDecimal.valueOf(Double.valueOf(7.349)).multiply(Constants.BIG_DECIMAL_TEN.pow(22));
        System.out.println("月球的质量为：" + moonQuality + "kg");

        System.out.println("地球的质量是月球质量的：" + earthQuality.divide(moonQuality, 10, BigDecimal.ROUND_DOWN) + "倍");
    }

    private static void testSort() {
        int array[] = buildRandomArray(10, 100);
        System.out.print("排序前：");
        printArray(array);

//        bubbleSort(array);
//        selectionSort(array);
        insertionSort(array);

        System.out.print("排序后：");
        printArray(array);
    }
}
