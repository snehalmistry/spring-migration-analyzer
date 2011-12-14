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

public interface FileSystemEntryOrganizer<T> {

    /**
     * Organizes File system entries by group & order.
     * 
     * T is name of organized collection returned.
     * 
     * @param list of file system entries to analyze
     * @param Output path generator implementation which generates relative path
     * 
     */
    T organize(Set<FileSystemEntry> fileSystemEntries, OutputPathGenerator locationAwarePathGenerator);
}
