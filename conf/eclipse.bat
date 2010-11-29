cd ..

cmd /c mvn eclipse:configure-workspace -Declipse.workspace=../noc

cd noc-define
cmd /c mvn eclipse:clean
cmd /c mvn eclipse:eclipse
cd ..

cd noc-frame
cmd /c mvn eclipse:clean
cmd /c mvn eclipse:eclipse
cd ..

cd noc-engine
cmd /c mvn eclipse:clean
cmd /c mvn eclipse:eclipse
cd ..

cd test-kao
cmd /c mvn eclipse:clean
cmd /c mvn eclipse:eclipse
cd ..

cd test-ooj
cmd /c mvn eclipse:clean
cmd /c mvn eclipse:eclipse
cd ..

cd noc-site
cmd /c mvn eclipse:clean
cmd /c mvn eclipse:eclipse
cd ..

cd conf


