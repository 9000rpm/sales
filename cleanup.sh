# csv to output.csv
cat /data/output/part*.csv > /data/output/output.csv

# cleanup folder
rm /data/output/.part*
rm /data/output/part*
rm /data/output/._SUCCESS.crc
rm /data/output/_SUCCESS
