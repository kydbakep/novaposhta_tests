FROM maven:3.3.9-jdk-8
WORKDIR /usr/src/novaposhta
# Google Chrome

RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
	&& echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
	&& apt-get update -qqy \
	&& apt-get -qqy install google-chrome-stable \
	&& rm /etc/apt/sources.list.d/google-chrome.list \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
	&& sed -i 's/"$HERE\/chrome"/"$HERE\/chrome" --no-sandbox/g' /opt/google/chrome/google-chrome
#    && sed -i 's/"$HERE\/chrome"/"$HERE\/chrome" --headless --disable-gpu/g' /opt/google/chrome/google-chrome

# ChromeDriver

ARG CHROME_DRIVER_VERSION=2.31
RUN wget --no-verbose -O /tmp/chromedriver_linux64.zip https://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_linux64.zip \
	&& rm -rf /opt/chromedriver \
	&& unzip /tmp/chromedriver_linux64.zip -d /opt \
	&& rm /tmp/chromedriver_linux64.zip \
	&& cp /opt/chromedriver /usr/local/bin \
	&& mv /opt/chromedriver /opt/chromedriver-$CHROME_DRIVER_VERSION \
	&& chmod 755 /opt/chromedriver-$CHROME_DRIVER_VERSION \
	&& ln -fs /opt/chromedriver-$CHROME_DRIVER_VERSION /usr/bin/chromedriver
#ADD utils/xvfb-chrome /usr/bin/xvfb-chrome
#RUN ln -sf /usr/bin/xvfb-chrome /usr/bin/google-chrome
ENV CHROME_BIN /usr/bin/google-chrome

# Xvfb

RUN apt-get update -qqy \
	&& apt-get -qqy install xvfb \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/*
#ENV DISPLAY :99
ENV DISPLAY :0
#ADD start_test.sh /start_test.sh
#RUN chmod a+x /start_test.sh

# Rights for browser

RUN mkdir /.pki \
    && mkdir /.pki/nssdb \
    && chmod -R 755 /.pki \
    && chmod -R 755 /usr/src \
    && mkdir /target \
    && mkdir /target/screenshots \
    && chmod -R 755 /target

