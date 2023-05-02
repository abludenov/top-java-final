package com.abdddev.restaurantvoiting.web.user;

import com.abdddev.restaurantvoiting.HasIdAndEmail;
import com.abdddev.restaurantvoiting.repository.UserRepository;
import com.abdddev.restaurantvoiting.web.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UniqueMailValidator implements Validator {
    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";

    private final UserRepository repository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasIdAndEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        HasIdAndEmail user = (HasIdAndEmail) target;
        if (StringUtils.hasText(user.getEmail())) {
            repository.findByEmailIgnoreCase(user.getEmail())
                    .ifPresent(dbUser -> {
                        if (isUpdatingUser(target)) {
                            int dbId = dbUser.id();

                            if (updatingSelf(user, dbId) || updatingWithPathMatchingUri(dbId)) return;
                        }
                        errors.rejectValue("email", "", EXCEPTION_DUPLICATE_EMAIL);
                    });
        }
    }

    private boolean isUpdatingUser(Object target) {
        return request.getMethod().equals("PUT");
    }

    private boolean updatingSelf(HasIdAndEmail user, int dbId) {
        return user.getId() != null && dbId == user.id();
    }

    private boolean updatingWithPathMatchingUri(int dbId) {
        String requestURI = request.getRequestURI();
        return requestURI.endsWith("/" + dbId) || (dbId == SecurityUtil.authId() && requestURI.contains("/profile"));
    }
}