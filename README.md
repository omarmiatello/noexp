# Common libraries for NoExp project

[![](https://jitpack.io/v/omarmiatello/noexp.svg)](https://jitpack.io/#omarmiatello/noexp)

This library has 4 modules:
- Module [:dataclass:](#how-to-use-dataclass-module)
  - `data class` with [Kotlinx/Serialization](https://github.com/Kotlin/kotlinx.serialization)
- Module [:domain:]()
  - TODO
- Module [:categories-parser:]()
  - TODO
- Module [:app:]()
  - TODO


## How to use `dataclass` module

This module could be used for parse the NoExp requests, and for send back a response.

#### Setup `dataclass` module

Add this in your root `build.gradle` file:
```gradle
repositories {
    // ...
    maven { url "https://jitpack.io" }
}
```

Grab via Gradle (v4 or later):
```groovy
implementation 'com.github.omarmiatello.noexp:dataclass:0.2.1'
```

### Example

```kotlin
// TODO
```

## License

    MIT License
    
    Copyright (c) 2020 Omar Miatello
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.