package ru.ifmo.insys1.request;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import lombok.Data;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;

@Data
public class FileUploadForm {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream file;
}
