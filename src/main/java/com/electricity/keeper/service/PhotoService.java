package com.electricity.keeper.service;

import java.io.IOException;
import java.io.InputStream;

public interface PhotoService {
    InputStream getImage(long id) throws IOException;
    void saveImage(InputStream in, long historyId) throws IOException;
    void changeImage(InputStream in, long photoId) throws IOException;
    void removeImage(long photoId) throws IOException;
}
