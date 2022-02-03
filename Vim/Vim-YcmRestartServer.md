# Vim-YcmRestartServer

The ycmd server SHUT DOWN (restart with ':YcmRestartServer')这种错误的解决方法

**安装完成后，终端输入vim使用底部会报这样的错：**

The ycmd server SHUT DOWN (restart with ':YcmRestartServer'). YCM core library not detected; you need to compile YCM before
using it. Follow the instructions in the documentation.
Press ENTER or type command to continue

**![img](https://www.codeprj.com/image/aHR0cHM6Ly9pbWFnZXMyMDE4LmNuYmxvZ3MuY29tL2Jsb2cvMTQ0MDg0NS8yMDE4MDkvMTQ0MDg0NS0yMDE4MDkwNzIzNTAxOTExNC0xNzM1MDQ4NjgwLnBuZw==.png)**

解决方法：

```
chan@ubuntu1804:/opt/k-vim$ cd ~
chan@ubuntu1804:~$ cd .vim/bundle/YouCompleteMe
chan@ubuntu1804:~/.vim/bundle/YouCompleteMe$ ./install.py
Searching Python 2.7 libraries...
Found Python library: /usr/lib/python2.7/config-x86_64-linux-gnu/libpython2.7.so
Found Python headers folder: /usr/include/python2.7
-- The C compiler identification is GNU 7.3.0
-- The CXX compiler identification is GNU 7.3.0
-- Check for working C compiler: /usr/bin/cc
-- Check for working C compiler: /usr/bin/cc -- works
-- Detecting C compiler ABI info
-- Detecting C compiler ABI info - done
-- Detecting C compile features
-- Detecting C compile features - done
-- Check for working CXX compiler: /usr/bin/c++
-- Check for working CXX compiler: /usr/bin/c++ -- works
-- Detecting CXX compiler ABI info
-- Detecting CXX compiler ABI info - done
-- Detecting CXX compile features
-- Detecting CXX compile features - done
-- Found PythonLibs: /usr/lib/python2.7/config-x86_64-linux-gnu/libpython2.7.so (found suitable version "2.7.15rc1", minimum required is "2.7") 
-- NOT using libclang, no semantic completion for C/C++/ObjC will be available
-- NOT using clang-tidy for static analysis.
-- Configuring done
-- Generating done
-- Build files have been written to: /tmp/ycm_build_dRh434
Scanning dependencies of target BoostParts
[  2%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/filesystem/src/codecvt_error_category.cpp.o
[  4%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/filesystem/src/operations.cpp.o
[  6%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/filesystem/src/path.cpp.o
[  9%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/filesystem/src/path_traits.cpp.o
[ 11%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/filesystem/src/portability.cpp.o
[ 13%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/filesystem/src/unique_path.cpp.o
[ 16%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/filesystem/src/utf8_codecvt_facet.cpp.o
[ 18%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/filesystem/src/windows_file_codecvt.cpp.o
[ 20%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/c_regex_traits.cpp.o
[ 23%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/cpp_regex_traits.cpp.o
[ 25%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/cregex.cpp.o
[ 27%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/fileiter.cpp.o
[ 30%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/icu.cpp.o
[ 32%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/instances.cpp.o
[ 34%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/posix_api.cpp.o
[ 37%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/regex.cpp.o
[ 39%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/regex_debug.cpp.o
[ 41%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/regex_raw_buffer.cpp.o
[ 44%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/regex_traits_defaults.cpp.o
[ 46%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/static_mutex.cpp.o
[ 48%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/usinstances.cpp.o
[ 51%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/w32_regex_traits.cpp.o
[ 53%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/wc_regex_traits.cpp.o
[ 55%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/wide_posix_api.cpp.o
[ 58%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/regex/src/winstances.cpp.o
[ 60%] Building CXX object BoostParts/CMakeFiles/BoostParts.dir/libs/system/src/error_code.cpp.o
[ 62%] Linking CXX static library libBoostParts.a
[ 62%] Built target BoostParts
Scanning dependencies of target ycm_core
[ 65%] Building CXX object ycm/CMakeFiles/ycm_core.dir/Candidate.cpp.o
[ 67%] Building CXX object ycm/CMakeFiles/ycm_core.dir/CandidateRepository.cpp.o
[ 69%] Building CXX object ycm/CMakeFiles/ycm_core.dir/Character.cpp.o
[ 72%] Building CXX object ycm/CMakeFiles/ycm_core.dir/CharacterRepository.cpp.o
[ 74%] Building CXX object ycm/CMakeFiles/ycm_core.dir/CodePoint.cpp.o
[ 76%] Building CXX object ycm/CMakeFiles/ycm_core.dir/CodePointRepository.cpp.o
[ 79%] Building CXX object ycm/CMakeFiles/ycm_core.dir/IdentifierCompleter.cpp.o
[ 81%] Building CXX object ycm/CMakeFiles/ycm_core.dir/IdentifierDatabase.cpp.o
[ 83%] Building CXX object ycm/CMakeFiles/ycm_core.dir/IdentifierUtils.cpp.o
[ 86%] Building CXX object ycm/CMakeFiles/ycm_core.dir/PythonSupport.cpp.o
[ 88%] Building CXX object ycm/CMakeFiles/ycm_core.dir/Result.cpp.o
[ 90%] Building CXX object ycm/CMakeFiles/ycm_core.dir/Utils.cpp.o
[ 93%] Building CXX object ycm/CMakeFiles/ycm_core.dir/Word.cpp.o
[ 95%] Building CXX object ycm/CMakeFiles/ycm_core.dir/versioning.cpp.o
[ 97%] Building CXX object ycm/CMakeFiles/ycm_core.dir/ycm_core.cpp.o
[100%] Linking CXX shared library /opt/k-vim/bundle/YouCompleteMe/third_party/ycmd/ycm_core.so
[100%] Built target ycm_core
-- The C compiler identification is GNU 7.3.0
-- Check for working C compiler: /usr/bin/cc
-- Check for working C compiler: /usr/bin/cc -- works
-- Detecting C compiler ABI info
-- Detecting C compiler ABI info - done
-- Detecting C compile features
-- Detecting C compile features - done
-- Found PythonLibs: /usr/lib/python2.7/config-x86_64-linux-gnu/libpython2.7.so (found version "2.7.15rc1") 
-- Configuring done
-- Generating done
-- Build files have been written to: /tmp/regex_build__rzBEy
Scanning dependencies of target _regex
[ 33%] Building C object CMakeFiles/_regex.dir/regex_2/_regex.c.o
[ 66%] Building C object CMakeFiles/_regex.dir/regex_2/_regex_unicode.c.o
[100%] Linking C shared library /opt/k-vim/bundle/YouCompleteMe/third_party/ycmd/third_party/cregex/regex_2/_regex.so
[100%] Built target _regex
chan@ubuntu1804:~/.vim/bundle/YouCompleteMe$ 
```

![img](https://www.codeprj.com/image/aHR0cHM6Ly9pbWFnZXMyMDE4LmNuYmxvZ3MuY29tL2Jsb2cvMTQ0MDg0NS8yMDE4MDkvMTQ0MDg0NS0yMDE4MDkwODAwMTAzOTA0OS0yMDUzMjAzMzgucG5n.png)

 

终端再次输入vim使用正常

![img](https://www.codeprj.com/image/aHR0cHM6Ly9pbWFnZXMyMDE4LmNuYmxvZ3MuY29tL2Jsb2cvMTQ0MDg0NS8yMDE4MDkvMTQ0MDg0NS0yMDE4MDkwODAwMTIzMDEzOS0xMjg2NzQ0NTcxLnBuZw==.png)

 