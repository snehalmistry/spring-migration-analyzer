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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.migrationanalyzer.analyze.fs.FileSystemEntry;
import org.springframework.migrationanalyzer.render.OutputPathGenerator;
import org.springframework.migrationanalyzer.util.Tree;

public class HierarchicalFileSystemEntryOrganizerTests {

    private final RootAwareOutputPathGenerator outputPathGenerator = new StubOutputPathGenerator("target");

    @Test
    public void testOrganize() {
        Set<FileSystemEntry> entries = new HashSet<FileSystemEntry>();
        entries.add(new StubFileSystemEntry("/abc/bbc.war/WEB-INF/lib/jpg.jar/cd.class"));
        entries.add(new StubFileSystemEntry("/abc/bbc.war/WEB-INF/lib/jpg.jar/ef.class"));
        entries.add(new StubFileSystemEntry("/abc/bbc.war/WEB-INF/lib/abc.jar/gh.class"));
        entries.add(new StubFileSystemEntry("/resources/xmls/pure.xml"));
        entries.add(new StubFileSystemEntry("/resources/abc.wars/other.xml"));

        String contentsPath = this.outputPathGenerator.generatePathForFileSystemEntryContents();
        OutputPathGenerator locationAwarePathGenerator = new LocationAwareOutputPathGenerator(this.outputPathGenerator, contentsPath);

        HierarchicalFileSystemEntryOrganizer organizer = new HierarchicalFileSystemEntryOrganizer();
        Tree<String> generatedTree = organizer.organize(entries, locationAwarePathGenerator);

        System.out.println(generatedTree.printTree(1));

        // Level 1: Root Nodes
        ArrayList<Tree<String>> children1 = new ArrayList<Tree<String>>(generatedTree.getChildren());
        assertTrue(children1.get(0).getHead().equals("/abc/bbc.war"));
        assertTrue(children1.get(1).getHead().equals("other.xml"));
        assertTrue(children1.get(2).getHead().equals("pure.xml"));

        // Level 2: Files under /abc/bbc.war
        ArrayList<Tree<String>> children2 = new ArrayList<Tree<String>>(children1.get(0).getChildren());
        assertTrue(children2.get(0).getHead().equals("/WEB-INF/lib/abc.jar"));
        assertTrue(children2.get(1).getHead().equals("/WEB-INF/lib/jpg.jar"));

        // Level 3: Files under /WEB-INF/lib/abc.jar
        ArrayList<Tree<String>> children31 = new ArrayList<Tree<String>>(children2.get(0).getChildren());
        assertTrue(children31.get(0).getHead().equals("gh.class"));

        // Level 3: Files under /WEB-INF/lib/jpg.jar
        ArrayList<Tree<String>> children32 = new ArrayList<Tree<String>>(children2.get(1).getChildren());
        assertTrue(children32.get(0).getHead().equals("cd.class"));
        assertTrue(children32.get(1).getHead().equals("ef.class"));

    }
}
