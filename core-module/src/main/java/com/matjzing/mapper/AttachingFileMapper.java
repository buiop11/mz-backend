package com.matjzing.mapper;

import com.matjzing.dto.common.RedisCacheKey;
import com.matjzing.dto.file.*;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachingFileMapper {

    @CacheEvict(value = RedisCacheKey.FILE_DEL, allEntries = true, cacheManager = "cacheManager")
    int insertAttachingFile(FileInsertRequest req);
    @CacheEvict(value = RedisCacheKey.FILE_DEL, allEntries = true, cacheManager = "cacheManager")
    int deleteAttachingFile(FileInsertRequest req);
    @CacheEvict(value = RedisCacheKey.FILE_DEL, allEntries = true, cacheManager = "cacheManager")
    int deactivateAttachingFileByTarget(FileInsertRequest req);
    @Cacheable(value = RedisCacheKey.FILE, cacheManager = "cacheManager10minutes", keyGenerator = "redisCacheKeyGenerator")
    FileSelectResponse selectFile(FileInsertRequest req);

    List<FileSelectResponse> getFileList(FileSelectRequest req);
    List<FileSelectResponse> getFile(FileSelectRequest req);
    
}
