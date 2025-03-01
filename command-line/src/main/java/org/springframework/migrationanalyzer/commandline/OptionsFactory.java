/*
 * Copyright 2010 the original author or authors.
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

package org.springframework.migrationanalyzer.commandline;

import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

final class OptionsFactory {

    public static final char OPTION_KEY_INPUT_PATH = 'i';

    public static final char OPTION_KEY_OUTPUT_PATH = 'o';

    public static final char OPTION_KEY_OUTPUT_TYPE = 't';

    public static final char OPTION_KEY_EXCLUDE = 'e';

    @SuppressWarnings("static-access")
    Options create() {
        Options options = new Options();

        options.addOption(OptionBuilder //
        .withDescription(
            "The path of the input location; either a single archive or a directory. In the case of a directory, the entire directory structure is examined and all archives that are found are analyzed") //
        .isRequired() //
        .hasArg() //
        .withArgName("inputPath") //
        .create(OPTION_KEY_INPUT_PATH));

        options.addOption(OptionBuilder //
        .withDescription("The type of the output to be created. Defaults to html.") //
        .hasArg() //
        .withArgName("outputType") //
        .create(OPTION_KEY_OUTPUT_TYPE));

        options.addOption(OptionBuilder //
        .withDescription("The path of the output directory. Defaults to the current working directory") //
        .hasArg() //
        .withArgName("outputPath") //
        .create(OPTION_KEY_OUTPUT_PATH));

        options.addOption(OptionBuilder //
        .withDescription("Paths within the input to be excluded from the analysis. Supports Ant-style patterns and can be specified multiple times.") //
        .hasArg() //
        .withArgName("excludedPaths") //
        .withLongOpt("exclude") //
        .isRequired(false) //
        .create(OPTION_KEY_EXCLUDE));

        return options;
    }
}
