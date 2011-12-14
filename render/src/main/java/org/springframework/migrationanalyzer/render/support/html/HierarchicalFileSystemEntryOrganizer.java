/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.migrationanalyzer.render.support.html;

import java.util.Set;

import org.springframework.migrationanalyzer.analyze.fs.FileSystemEntry;
import org.springframework.migrationanalyzer.render.OutputPathGenerator;
import org.springframework.migrationanalyzer.util.Tree;

/**
 * Organize file entries by hierarchy of archive files.
 * 
 */
public class HierarchicalFileSystemEntryOrganizer implements FileSystemEntryOrganizer<Tree<String>> {

    private static final String PATH_SEPARATOR = "/";

    private static final char PATH_SEPARATOR_CHAR = '/';

    private static final int SUFFIX_SIZE = 4;

    private static final String[] ARCHIVE_SUFFIXES = { ".war", ".ear", ".jar", ".rar", ".zip" };

    /**
     * It organizes file system entries by its parent archive files. It parses file name of file system entry to extract
     * archive names by their suffixes like ear,war,jar,zip,etc and stores in tree structure.
     * 
     */
    @Override
    public Tree<String> organize(Set<FileSystemEntry> fileSystemEntries, OutputPathGenerator locationAwarePathGenerator) {
        Tree<String> rootTree = new Tree<String>(PATH_SEPARATOR);
        for (FileSystemEntry fileSystemEntry : fileSystemEntries) {
            int index = 0;
            int baseIndex = 0;
            Tree<String> baseTree = rootTree;
            String fileName = fileSystemEntry.getName();
            while (index < fileName.length()) {
                char c = fileName.charAt(index);
                int endIndex = index + SUFFIX_SIZE;
                if ((c == '.') && (endIndex < fileName.length()) && (fileName.charAt(endIndex) == PATH_SEPARATOR_CHAR)) {
                    String substring = fileName.substring(index, endIndex);
                    if (isArchiveSuffix(substring)) {
                        substring = fileName.substring(baseIndex, endIndex);
                        baseTree = baseTree.addChildIfAbsent(substring);
                        baseIndex = endIndex;
                    }
                    index = endIndex;

                }
                index++;
            }
            baseTree = baseTree.addChildIfAbsent(getBaseName(fileName));
            baseTree = baseTree.addChildIfAbsent(locationAwarePathGenerator.generatePathFor(fileSystemEntry));
        }

        return rootTree;
    }

    private boolean isArchiveSuffix(String str) {
        for (String suffix : ARCHIVE_SUFFIXES) {
            if (suffix.equals(str)) {
                return true;
            }
        }

        return false;
    }

    private String getBaseName(String path) {
        if (path != null) {
            return path.substring(path.lastIndexOf('/') + 1);
        }
        return null;
    }

}
