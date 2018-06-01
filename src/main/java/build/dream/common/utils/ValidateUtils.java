package build.dream.common.utils;

import org.apache.commons.lang.Validate;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ValidateUtils {
    private static Validator validator;

    private static Validator obtainValidator() {
        if (validator == null) {
            validator = ApplicationHandler.obtainValidator();
        }
        return validator;
    }

    public static List<Field> obtainAllFields(Class<?> modelClass) {
        List<Field> allFields = new ArrayList<Field>();
        while (modelClass != Object.class) {
            Field[] fields = modelClass.getDeclaredFields();
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isNative(modifiers)) {
                    continue;
                }
                allFields.add(field);
            }
            modelClass = modelClass.getSuperclass();
        }

        return allFields;
    }

    public static boolean validate(Object model) {
        Class<?> modelClass = model.getClass();
        Validator validator = obtainValidator();
        List<Field> allFields = obtainAllFields(modelClass);
        for (Field field : allFields) {
            Iterator<ConstraintViolation<Object>> iterator = validator.validateProperty(model, field.getName()).iterator();
            if (iterator.hasNext()) {
                return false;
            }
        }
        return true;
    }

    public static void validateAndThrow(Object model) {
        Validator validator = obtainValidator();
        List<Field> allFields = obtainAllFields(model.getClass());
        for (Field field : allFields) {
            Iterator<ConstraintViolation<Object>> iterator = validator.validateProperty(model, field.getName()).iterator();
            if (iterator.hasNext()) {
                Validate.isTrue(false, ApplicationHandler.obtainParameterErrorMessage(field.getName()));
            }
        }
    }
}
