package com.github.peacetrue.template.model.structure;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Function;

/**
 * @author xiayx
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
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                this.move(dir);
                return super.preVisitDirectory(dir, attrs);
            }

            private void move(Path dir) throws IOException {
                Path convertedPath = converter.apply(dir);
                if (convertedPath.equals(dir)) return;
                Files.move(dir, convertedPath, StandardCopyOption.REPLACE_EXISTING);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                this.move(file);
                return super.visitFile(file, attrs);
            }
        });
    }


    @Test
    public void name() throws IOException {
        String path = "/Users/xiayx/Documents/Projects/peacetrue-template-model/peacetrue-template-model-structure/src/main/resources/template-model-structure";
        rename(Paths.get(path), path1 -> path1.resolveSibling(path1.getFileName().toString().replaceAll("(\\{.*?})", "\\$$1")));
    }


}
