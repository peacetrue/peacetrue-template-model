package com.github.peacetrue.template.model.structure;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Function;

/**
 * @author peace
 */
public class FileTest {

    /**
     * 重命名
     *
     * @param path 路径
     * @throws IOException
     */
    public static void rename(Path path, Function<Path, Path> converter) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

            private boolean move(Path dir) throws IOException {
                Path convertedPath = converter.apply(dir);
                if (convertedPath.equals(dir)) return false;
                Files.move(dir, convertedPath, StandardCopyOption.REPLACE_EXISTING);
                return true;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                this.move(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                this.move(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }


    @Test
    public void name() throws IOException {
        String path = "/Users/xiayx/Documents/Projects/peacetrue-template-model/peacetrue-template-model-structure/src/main/resources/template-model-structure";
//        path = "/Users/xiayx/Documents/Projects/peacetrue-template-model/peacetrue-template-model-content/src/main/resources/template-model-content";
        rename(Paths.get(path), path1 -> path1.resolveSibling(path1.getFileName().toString().replaceAll("basePackageName", "basePackagePath")));
    }


}
