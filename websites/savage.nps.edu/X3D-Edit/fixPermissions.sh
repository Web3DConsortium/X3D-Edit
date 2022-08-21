#!/bin/sh
# invocation:  fixPermissions.sh
# savage.nps.edu/var/www/html/X3D-Edit

sudo chmod 664           *.* images/*.* models/*.* hold/*.* archive/*.*
sudo chmod 775           fixPermissions.sh
sudo chmod 775               images     models     hold     archive
sudo chown apache:apache *.* images/*.* models/*.* hold/*.* archive/*.*
ls -al                   *   images/*   models/*   hold/*   archive/*.*
