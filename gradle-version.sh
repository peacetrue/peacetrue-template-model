#!/bin/bash

#find ~/Documents/Projects -name "gradle-wrapper.properties" | xargs grep "6.1.1"
find ~/Documents/Projects -name "gradle-wrapper.properties" |
  xargs sed -i "" "s/6.1.1/6.4/g"

#| xargs grep "6.1.1" \
#//sed -i "" "s/$css/$css_prd/g"
