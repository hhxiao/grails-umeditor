/**
 Copyright Hai-Hua Xiao

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package org.grails.plugin.umeditor

import grails.util.Environment

class UmeditorTagLib {
    static namespace = "umeditor"
    def umeditorConfigService

    def resources = { attrs ->
        def minified = true
        if (Environment.current != Environment.PRODUCTION) {
            minified = attrs?.minified ? attrs?.minified == 'true' : true
        }
        def editor = umeditorConfigService.newEditor(request)
        String lang = attrs.lang ?: umeditorConfigService.resolveLang(request)
        out << editor.renderResources(g, minified, lang)
    }
 
    def toolbar = { attrs, body ->
        String type = attrs.remove('type') ?: 'default'
        String value = attrs.value ?: body()
        def editor = umeditorConfigService.newEditor(request)
        out << editor.renderToolbar(g, type, value, attrs)
    }

    def editor = { attrs, body ->
        if (!attrs.id) throwTagError("Tag [editor] is missing required attribute [id]")
        String id = attrs.remove('id')
        String value = attrs.value ?: body()
        def editor = umeditorConfigService.newEditor(request)
        out << editor.renderEditor(g, id, value, attrs)
    }
}
