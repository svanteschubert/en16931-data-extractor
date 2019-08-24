/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.prototypefund.en16931.type;

import java.util.Comparator;

public class NumberAwareStringComparator implements Comparator<String> {

    public int compare(String s1, String s2) {

        String[] s1Parts = s1.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        String[] s2Parts = s2.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

        int i = 0;
        while (i < s1Parts.length && i < s2Parts.length) {

            //if parts are the same
            if (s1Parts[i].compareTo(s2Parts[i]) == 0) {
                ++i;
            } else {
                try {

                    int intS1 = Integer.parseInt(s1Parts[i]);
                    int intS2 = Integer.parseInt(s2Parts[i]);

                    //if the parse works
                    int diff = intS1 - intS2;
                    if (diff == 0) {
                        ++i;
                    } else {
                        return diff;
                    }
                } catch (Exception ex) {
                    return s1.compareTo(s2);
                }
            }//end else
        }//end while

        //Handle if one string is a prefix of the other.
        // nothing comes before something.
        if (s1.length() < s2.length()) {
            return -1;
        } else if (s1.length() > s2.length()) {
            return 1;
        } else {
            return 0;
        }
    }
}
