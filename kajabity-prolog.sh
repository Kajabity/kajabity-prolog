#!/usr/bin/env bash

#
# Copyright (c) 2003-2025 Simon J. Williams
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

# --------------------------------------------
# Run the Kajabity Prolog console application
# --------------------------------------------

set -e

BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAR_DIR="$BASE_DIR/prolog-console/target"
JAVA_OPTS="--enable-native-access=ALL-UNNAMED"

# Find the first matching JAR file
JAR_FILE=$(ls "$JAR_DIR"/prolog-console-*.jar 2>/dev/null | head -n 1)

if [[ -z "$JAR_FILE" ]]; then
  echo "ERROR: No prolog-console JAR found in $JAR_DIR" >&2
  exit 1
fi

echo
exec java $JAVA_OPTS -jar "$JAR_FILE" "$@"
