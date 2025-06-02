FROM erlang:25-slim
ENV DEBIAN_FRONTEND=noninteractive

# Install build tools and dependencies for Tsung
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    ca-certificates wget \
    make gcc autoconf automake libtool \
    libxml2-dev libexpat1-dev libssl-dev \
    perl libtemplate-perl libfile-spec-perl \
    libhtml-template-perl libgd-gd2-perl \
    gnuplot fontconfig && \
    update-ca-certificates && \
    \
    wget https://github.com/processone/tsung/archive/refs/tags/v1.8.0.tar.gz && \
    tar -xzf v1.8.0.tar.gz && \
    cd tsung-1.8.0 && \
    ./configure && make && make install && \
    cd / && rm -rf tsung-1.8.0 v1.8.0.tar.gz && \
    \
    ln -s /usr/local/share/tsung/bin/tsung_stats.pl /usr/local/bin/tsung_stats.pl && \
    \
    apt-get purge -y gcc autoconf automake libtool && \
    apt-get autoremove -y && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Extra tools for debugging (optional)
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    iproute2 net-tools iputils-ping \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /root/.tsung/log

CMD bash -c "tsung -f /tsung/config/warm.xml -l /tsung/log/0 start && \
             tsung -f /tsung/config/tsung1.xml -l /tsung/log/1 start && \
             cd /tsung/log/1/\$(ls -1t /tsung/log/1 | head -n 1) && \
             /usr/local/lib/tsung/bin/tsung_stats.pl && \
             tsung -f /tsung/config/tsung2.xml -l /tsung/log/2 start && \
             cd /tsung/log/2/\$(ls -1t /tsung/log/2 | head -n 1) && \
             /usr/local/lib/tsung/bin/tsung_stats.pl && \
             tsung -f /tsung/config/tsung3.xml -l /tsung/log/3 start && \
             cd /tsung/log/3/\$(ls -1t /tsung/log/3 | head -n 1) && \
             /usr/local/lib/tsung/bin/tsung_stats.pl && \
             tsung -f /tsung/config/stress.xml -l /tsung/log/4 start && \
             cd /tsung/log/4/\$(ls -1t /tsung/log/4 | head -n 1) && \
             /usr/local/lib/tsung/bin/tsung_stats.pl && \
             "

WORKDIR /tsung