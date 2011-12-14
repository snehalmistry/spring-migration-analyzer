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

package org.springframework.migrationanalyzer.util;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TreeTests {

    private final static String PLANT = "plants";

    private final static String VEGETABLE = "vegetables";

    private final static String FRUIT = "fruits";

    private final static String APPLE = "apple";

    private final static String RED_APPLE = "red-apple";

    private final static String YELLOW_APPLE = "yellow-apple";

    private final static String BANANA = "banana";

    private final static String ORANGE = "orange";

    private final static String TOMATO = "tomato";

    private final static String UPPERCASE_TOMATO = "Tomato";

    private Tree<String> plant;

    private Tree<String> vegetable;

    private Tree<String> fruit;

    @Before
    public void createTree() {
        this.plant = new Tree<String>(PLANT);
        this.vegetable = this.plant.addChild(VEGETABLE);
        this.vegetable.addChild(TOMATO);
        this.vegetable.addChild(UPPERCASE_TOMATO);

        this.fruit = this.plant.addChild(FRUIT);
        Tree<String> apple = this.fruit.addChild(APPLE);
        apple.addChild(RED_APPLE);
        apple.addChild(YELLOW_APPLE);
        this.fruit.addChild(BANANA);
        this.fruit.addChild(ORANGE);
    }

    @Test
    public void testOrderOfTree() {
        String preOrderChecksum = PLANT + FRUIT + APPLE + RED_APPLE + YELLOW_APPLE + BANANA + ORANGE + VEGETABLE + UPPERCASE_TOMATO + TOMATO;
        StringBuffer sb = new StringBuffer();
        createPreorderchecksum(this.plant, sb);
        assertTrue(sb.toString().equals(preOrderChecksum));
    }

    private void createPreorderchecksum(Tree<String> node, StringBuffer strBuffer) {
        strBuffer.append(node.getHead());
        for (Tree<String> child : node.getChildren()) {
            createPreorderchecksum(child, strBuffer);
        }
    }

    @Test
    public void testDuplicateOverwriteOnAddChild() {
        Tree<String> veg = this.plant.addChild(VEGETABLE);
        assertTrue(veg.getChildren().size() == 0);
    }

    @Test
    public void testAddChildIfAbsent() {
        Tree<String> veg = this.plant.addChildIfAbsent(VEGETABLE);
        assertTrue(veg.getChildren().size() == 2);
    }

    @Test
    public void testHasElement() {
        assertTrue(this.plant.hasElemenent(VEGETABLE));
        assertTrue(this.vegetable.hasElemenent(UPPERCASE_TOMATO));
    }

    @Test
    public void testFirstChildLeaf() {
        assertTrue(this.vegetable.isFirstChildLeaf());
    }

    @Test
    public void testLeaf() {
        for (Tree<String> child : this.vegetable.getChildren()) {
            assertTrue(child.isLeaf());
        }

    }

}
