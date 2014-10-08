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

class Umeditor {
    def homePath

    Umeditor(def grailsApplication, String homePath) {
        this.homePath = homePath
    }

    def renderResources(def g, def minified, String lang) {
        return """
<link href="${homePath}/themes/default/css/umeditor${minified ? '.min' : ''}.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${homePath}/umeditor.config.js"></script>
<script type="text/javascript" src="${homePath}/umeditor${minified ? '.min' : ''}.js"></script>
<script type="text/javascript" src="${homePath}/lang/${lang}/${lang}.js"></script>
<script type="text/javascript">
    window.UMEDITOR_CONFIG.home = "${homePath}/";
    window.UMEDITOR_CONFIG.imageUrl = "${g.createLink(controller: 'umeditorHandler', action: 'upload')}";
    window.UMEDITOR_CONFIG.imagePath = "${g.createLink(controller: 'umeditorHandler', action: 'images')}/";
    window.UMEDITOR = {config:{default:{}},instance:{}};
</script>
"""
    }

    def renderToolbar(def g, String type, String initialValue, Map attrs) {
        StringBuilder buf = new StringBuilder()
        if(!initialValue) {
            initialValue = g.message(code: "umeditor.toolbar.${type}", default: "source | undo redo | bold italic underline strikethrough | forecolor backcolor | fontsize")
        }
        List<String> buttons = initialValue.split(',')
        buf << """
<script type="text/javascript">
    window.UMEDITOR_CONFIG.toolbar = [${buttons.collect{"\"${it.trim()}\""}.join(',')}];
</script>"""
        return buf.toString()
    }

    def renderEditor(def g, String instanceId, String initialValue, Map attrs) {
        StringBuilder buf = new StringBuilder()
        buf << """
<textarea id="${instanceId}" ${attrs.collect {it.key + '="' + it.value + '"'}.join(' ')}>${initialValue}</textarea>
<script type="text/javascript">
    UMEDITOR.instance.${instanceId} = UM.getEditor("${instanceId}");
</script>"""
        return buf.toString()
    }
}
