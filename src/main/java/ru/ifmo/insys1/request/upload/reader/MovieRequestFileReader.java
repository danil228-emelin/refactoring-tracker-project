package ru.ifmo.insys1.request.upload.reader;

import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.FileUploadForm;
import ru.ifmo.insys1.request.upload.UploadMovie;

import java.util.List;

public interface MovieRequestFileReader {

    List<UploadMovie> read(FileUploadForm file) throws ServiceException;
}
