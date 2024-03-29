/*
 * Copyright 2009 the original author or authors.
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
package org.gradle.api;

import groovy.lang.Closure;
import org.gradle.api.provider.Provider;
import org.gradle.api.specs.Spec;

import java.util.Collection;

/**
 * <p>A {@code DomainObjectCollection} is a specialised {@link Collection} that adds the ability to receive modification notifications and use live filtered sub collections.</p>
 *
 * <p>The filtered collections returned by the filtering methods, such as {@link #matching(Closure)}, return collections that are <em>live</em>. That is, they reflect
 * changes made to the source collection that they were created from. This is true for filtered collections made from filtered collections etc.</p>
 * <p>
 * You can also add actions which are executed as elements are added to the collection. Actions added to filtered collections will be fired if an addition/removal
 * occurs for the source collection that matches the filter.</p>
 * <p>
 * {@code DomainObjectCollection} instances are not <em>thread-safe</em> and undefined behavior may result from the invocation of any method on a collection that is being mutated by another
 * thread; this includes direct invocations, passing the collection to a method that might perform invocations, and using an existing iterator to examine the collection.
 * </p>
 * 
 * @param <T> The type of domain objects in this collection.
 */
public interface DomainObjectCollection<T> extends Collection<T> {
    /**
     * Adds an element to this collection, given a {@link Provider} that will provide the element when required.
     *
     * <strong>Note: this method currently has a placeholder name and will almost certainly be renamed.</strong