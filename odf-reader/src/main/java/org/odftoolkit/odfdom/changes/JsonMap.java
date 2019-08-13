/*
 * Copyright 2012 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS952" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.odftoolkit.odfdom.changes;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;


/**
 * Offering a Map Interface for a JSONObject, which uses internally a HashMap, but is not offering the interface.
 */
public class JsonMap extends JSONObject implements Map {

    /** Required by Map interface, but JSONObject only allows protected access */
    public Set<Entry<String,Object>> entrySet(){
        return super.entrySet();
    }
    
    @Override
    public int size() {
        return super.length();
    }

    @Override   
    public boolean containsKey(Object key) {
        return super.has(key.toString());
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object get(Object key) {
        return super.get(key.toString());
    }

    @Override
    public Object put(Object key, Object value) {
        return super.put(key.toString(), value);
    }

    @Override
    public Object remove(Object key) {
        return super.remove(key.toString());
    }

    @Override
    public void putAll(Map m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection values() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}