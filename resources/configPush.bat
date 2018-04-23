@echo on

SET commitMessage=%1

CD commonbuild-configuration

git commit -a -m %commitMessage%

:Wait
ping 127.0.0.1 -n 6 > nul

git pull origin master
git push --set-upstream origin master && (
   echo git push was successful
) || (
   echo trying git pull again after wait
   GOTO Wait
)

@echo on
