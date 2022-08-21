#!/bin/sh
echo invocation:  sudo fixPermissions.sh
# www.movesInstitute.org/var/www/MovesWebSiteDirectories/X3D-Edit/netbeans_modules

chmod 664           *.* hold/*.* licenses/*.*
chmod 774           fixPermissions.sh
chmod 775               hold     licenses
chown apache:apache *.* hold/*.* licenses/*.*
ls -al               .  hold/*   licenses/*
