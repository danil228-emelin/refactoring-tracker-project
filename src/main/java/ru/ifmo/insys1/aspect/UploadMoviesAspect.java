package ru.ifmo.insys1.aspect;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.request.ImportRequest;
import ru.ifmo.insys1.service.ImportService;

@Slf4j
@ApplicationScoped
public class UploadMoviesAspect {

    @Inject
    private ImportService importService;

    @AroundInvoke
    public Object process(InvocationContext context) throws Exception {
        try {
            Object proceed = context.proceed();
            log.info("Successfully uploaded movies");
            return proceed;
        } catch (Exception e) {
            log.error("Error while uploading movies, save failed import", e);
            importService.persist(new ImportRequest(false, 0));
            throw e;
        }
    }
}
