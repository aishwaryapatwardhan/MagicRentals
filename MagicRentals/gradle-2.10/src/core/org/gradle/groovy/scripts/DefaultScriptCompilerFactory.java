/*
 * Copyright 2010 the original author or authors.
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
package org.gradle.groovy.scripts;

import org.codehaus.groovy.ast.ClassNode;
import org.gradle.api.Action;
import org.gradle.api.internal.initialization.ClassLoaderIds;
import org.gradle.api.internal.initialization.loadercache.ClassLoaderId;
import org.gradle.groovy.scripts.internal.CompiledScript;
import org.gradle.groovy.scripts.internal.CompileOperation;
import org.gradle.groovy.scripts.internal.ScriptClassCompiler;
import org.gradle.groovy.scripts.internal.ScriptRunnerFactory;

public class DefaultScriptCompilerFactory implements ScriptCompilerFactory {
    private final ScriptRunnerFactory scriptRunnerFactory;
    private final ScriptClassCompiler scriptClassCompiler;

    public DefaultScriptCompilerFactory(ScriptClassCompiler scriptClassCompiler, ScriptRunnerFactory scriptRunnerFactory) {
        this.scriptClassCompiler = scriptClassCompiler;
        this.scriptRunnerFactory = scriptRunnerFactory;
    }

    public ScriptCompiler createCompiler(ScriptSource source) {
        return new ScriptCompilerImpl(source);
    }

    private class ScriptCompilerImpl implements ScriptCompiler {
        private final ScriptSource source;

        public ScriptCompilerImpl(ScriptSource source) {
            this.source = new CachingScriptSource(source);
        }

        @Override
        public <T extends Script, M> ScriptRunner<T, M> compile(Class<T> scriptType, CompileOperation<M> extractingTransformer, ClassLoader classloader, Action<? super ClassNode> verifier) {
            ClassLoaderId classLoaderId = ClassLoaderIds.buildScript(source.getFileName(), extractingTransformer.getId());
            CompiledScript<T, M> compiledScript = scriptClassCompiler.compile(source, classloader, classLoaderId, extractingTransformer, scriptType, verifier);
            return scriptRunnerFactory.create(compiledScript, source, classloader);
        }
    }
}
