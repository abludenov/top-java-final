package com.abdddev.restaurantvoiting.util.validation;

import com.abdddev.restaurantvoiting.HasId;
import com.abdddev.restaurantvoiting.error.IllegalRequestDataException;
import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;

@UtilityClass
public class ValidationUtil {
    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    public static <T> T checkExisted(T obj, int id) {
        if (obj == null) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
        return obj;
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}