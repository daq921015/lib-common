package build.dream.common;

import build.dream.common.constants.Constants;
import build.dream.common.erp.catering.domains.Coupon;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * Created by liuyandong on 2017/7/25.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        Class<?> domainClass = Coupon.class;
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

        System.out.println(code.toString());
        System.out.println("public static Builder builder() {return new Builder();}");

        BigDecimal sunQuality = BigDecimal.valueOf(Double.valueOf(1.9891)).multiply(BigDecimal.TEN.pow(30));
        System.out.println(sunQuality);

        BigDecimal earthQuality = BigDecimal.valueOf(Double.valueOf(5.965)).multiply(BigDecimal.TEN.pow(24));
        System.out.println(earthQuality);

        System.out.println(sunQuality.divide(earthQuality, 10, BigDecimal.ROUND_DOWN));

        BigDecimal sunRadius = BigDecimal.valueOf(6.955).multiply(BigDecimal.TEN.pow(8));
        System.out.println(sunRadius);

        BigDecimal sunVolume = Constants.BIG_DECIMAL_FOUR.multiply(Constants.BIG_DECIMAL_PI).multiply(sunRadius.pow(3)).divide(Constants.BIG_DECIMAL_THREE, 10, BigDecimal.ROUND_DOWN);
        System.out.println(sunVolume);

        BigDecimal earthRadius = BigDecimal.valueOf(6371000);
        BigDecimal earthVolume = Constants.BIG_DECIMAL_FOUR.multiply(Constants.BIG_DECIMAL_PI).multiply(earthRadius.pow(3)).divide(Constants.BIG_DECIMAL_THREE, 10, BigDecimal.ROUND_DOWN);
        System.out.println(earthVolume);

        System.out.println(sunVolume.divide(earthVolume, 10, BigDecimal.ROUND_DOWN));
    }
}
