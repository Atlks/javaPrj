set pathx="%~dp0"
set prjName=hojo_firePrj
set uuid=%date:/=-% %time::=-% 
 set  time=2016-06-01-01:01:01
set zipname="%~dp0%prjName%_%time::=.%_to_%uuid%.zip"
set dbname="wxb_srv_mir";

 --echo %date:/=-%   

  
 set rar_home="C:\Program Files\WinRAR\WinRAR.exe"
if not exist %rar_home% ( set rar_home=c:\WinRAR\WinRAR.exe)
if not exist %rar_home% ( set rar_home=d:\WinRAR\WinRAR.exe )
if not exist %rar_home% (
 set rar_home="C:\Program Files (x86)\WinRAR\WinRAR.exe"
 echo aaa
 )
 
 rem      ===========================�����ĳ��ʱ����Ժ��޸ĵ��ļ���ѹ����һ��������
 
%rar_home% a   -x*\build\ -x*\.svn\ -x*\dist\ -x*\nbproject\  -x*\.git\ -x*\target\  -x*.jar -ep1 -m1 -r -n*.bsh  -n*.properties -n*.csv -n*.classpath  -n*.project -n*.ini -n*.ahk  -n*.bat  -n*.dmp   -n*.jsp -n*.vm -n*.css -n*.java  -n*.xml -n*.dwt -n*.php -n*.txt -n*.doc -n*.cs -n*.aspx -n*.ascx -n*.htm -n*.html -n*.js -n*.csproj -n*.sln -n*.resx -n*.sql -n*.config -n*.xsd -n*.settings  -n*.bat -n*.txt -n*.tld  -n*.tag -n*.properties   -n*.buildpath  -n*.classpath  -n*.myhibernatedata  -n*.mymetadata  -n*.project  -n*.jsdtscope  -n*.prefs  -n*.component  -n*.xml  -n*.container  -n*.name  -n*.sql  -n*.bat  -n*.hql  -n*.  -n*.log  -n*.txt  -n*.java  -n*.html  -n*.groovy  -n*.p12  -n*.properties  -n*.jsp  -n*.js  -n*.htaccess  -n*.pack  -n*.css  -n*.htm  -n*.xsd  -n*.htc  -n*.php  -n*.json  -n*.example-php  -n*.as  -n*.svg  -n*.sh  -n*.template  -n*.smd  -n*.fla  -n*.tld  -n*.asp  -n*.aspx  -n*.ashx  -n*.MF  -n*.scc  -n*.eot  -n*.ttf  -n*.woff  -n*.application  -n*.page  -n*.smap -ta%time%  %zipname%   %pathx%


rem      ===========================���baseline ��Ŀ������Ŀ���ߡ�������ǰʱ������°���Ŀ���к����ļ���Ү�յ���ʱ����Ժ��޸ĵ��ļ���ѹ����һ��������
set zipname_baseline="%~dp0%prjName%_%uuid%_baselin.zip"
%rar_home% a   -x*\build\ -x*\.svn\ -x*\dist\ -x*\nbproject\ -x*\.git\   -x*\target\ -x*.jar -ep1 -m1 -r -n*.bsh  -n*.properties -n*.csv -n*.classpath  -n*.project -n*.ini -n*.ahk  -n*.bat  -n*.dmp   -n*.jsp -n*.vm -n*.css -n*.java  -n*.xml -n*.dwt -n*.php -n*.txt -n*.doc -n*.cs -n*.aspx -n*.ascx -n*.htm -n*.html -n*.js -n*.csproj -n*.sln -n*.resx -n*.sql -n*.config -n*.xsd -n*.settings  -n*.bat -n*.txt -n*.tld  -n*.tag -n*.properties   -n*.buildpath  -n*.classpath  -n*.myhibernatedata  -n*.mymetadata  -n*.project  -n*.jsdtscope  -n*.prefs  -n*.component  -n*.xml  -n*.container  -n*.name  -n*.sql  -n*.bat  -n*.hql  -n*.  -n*.log  -n*.txt  -n*.java  -n*.html  -n*.groovy  -n*.p12  -n*.properties  -n*.jsp  -n*.js  -n*.htaccess  -n*.pack  -n*.css  -n*.htm  -n*.xsd  -n*.htc  -n*.php  -n*.json  -n*.example-php  -n*.as  -n*.svg  -n*.sh  -n*.template  -n*.smd  -n*.fla  -n*.tld  -n*.asp  -n*.aspx  -n*.ashx  -n*.MF  -n*.scc  -n*.eot  -n*.ttf  -n*.woff  -n*.application  -n*.page  -n*.smap   %zipname_baseline%   %pathx%



rem      =============�����Ǵ��sql�ļ���������ݿⲢѹ��
set sql_bakName="%~dp0%prjName%_%uuid%.sql"
set sql_zip="%~dp0%prjName%_%uuid%.sql.zip"
C:\wamp\mysql\bin\mysqldump.exe -uroot -proot --set-charset=utf8 %dbname%  --result-file=%sql_bakName%
D:\wamp\bin\mysql\mysql5.5.20\bin\mysqldump.exe -uroot -proot --set-charset=utf8 %dbname% --result-file=%sql_bakName%
%rar_home% a -m1  %sql_zip% %sql_bakName%



pause


rem  ====================================================˵����

rem     pathxΪ����Ŀ¼��Ŀ¼���пո�����Ҫ��˫��������������Ŀ¼��β��Ҫ�з�б�ܡ���
rem     prjName  ��Ŀ����
rem      zipname ΪҪ������ļ���
rem     -time  ���ָ��ʲôʱ���Ժ��޸ĵ��ļ��������ļ��޸�ʱ��������ǡ�

rem     -n*.jsp  ����������������JSP�ļ������������ı��ļ��������� -n*.txt..�������ƿɡ����Ѿ����˴��java��Ŀ�ļ���������չ���ˣ�������©�������������
 
 
 