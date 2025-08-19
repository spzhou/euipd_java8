# Apache 2.0 代码头部模板

为了符合 Apache 2.0 许可证要求，所有新创建的源代码文件都应包含以下头部注释。

## Java 文件头部模板

```java
/*
 * Copyright 2025 周朔鹏
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```

## XML 文件头部模板

```xml
<!--
  ~ Copyright 2025 周朔鹏
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->
```

## Properties 文件头部模板

```properties
#
# Copyright 2025 周朔鹏
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
```

## YAML 文件头部模板

```yaml
#
# Copyright 2025 周朔鹏
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
```

## 批量添加头部脚本

可以使用以下脚本为现有文件批量添加 Apache 2.0 头部：

```bash
#!/bin/bash
# add_apache_headers.sh

# Java 文件头部
JAVA_HEADER='/*
 * Copyright 2025 周朔鹏
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */'

# 为所有 Java 文件添加头部（如果还没有）
find . -name "*.java" | while read file; do
    if ! grep -q "Licensed under the Apache License" "$file"; then
        echo "$JAVA_HEADER" > temp_file
        cat "$file" >> temp_file
        mv temp_file "$file"
        echo "Added header to $file"
    fi
done
```

## 注意事项

1. 现有的文件头部已包含作者和项目信息，在开源时可以选择性保留或替换
2. 新文件建议使用 Apache 2.0 标准头部
3. 确保版权年份为当前年份
4. 对于修改现有文件，可以在现有头部基础上添加 Apache 2.0 声明
