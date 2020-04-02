PROJECT_DIR="/root/codoc19"
#PROJECT_DIR="/home/theapache64/Documents/projects/codoc19"
RES_DIR="$PROJECT_DIR/covid19-api/src/main/resources"
wget "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv" -O "$RES_DIR/time_series_covid19_confirmed_global.csv"
wget "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv" -O "$RES_DIR/time_series_covid19_deaths_global.csv"
wget "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv" -O "$RES_DIR/time_series_covid19_recovered_global.csv"
wget "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_US.csv" -O "$RES_DIR/time_series_covid19_deaths_US.csv"
wget "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv" -O "$RES_DIR/time_series_covid19_confirmed_US.csv"
