cd ..
del /s /q build
mkdir build
cd build
cmake -G "Visual Studio 16" -A x64 -DWINDOWS10=ON -DUSE_VULKAN=ON -DSPIRV_SHADERC=OFF -DFFMPEG=ON ../neo
pause