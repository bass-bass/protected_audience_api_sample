FROM centos:7

WORKDIR /certification

COPY nginx.repo /etc/yum.repos.d/

RUN sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-* && \
    sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-* && \
    yum -y update && yum clean all && \
    # install nodejs, nginx
    yum install https://rpm.nodesource.com/pub_16.x/nodistro/repo/nodesource-release-nodistro-1.noarch.rpm -y && \
    yum -y install sudo nodejs lsof nsolid nginx java-11-openjdk && \
    # install mkcert
    curl -JLO "https://dl.filippo.io/mkcert/latest?for=linux/amd64" && \
    chmod +x mkcert-v*-linux-amd64 && \
    mv mkcert-v*-linux-amd64 /usr/local/bin/mkcert && \
    mkcert -install && \
    mkcert localhost && \
    mkdir certfiles && \
    mv *.pem certfiles && \
    mkdir -p /var/app/fs/dsp/log /kvsapi

COPY nginx.conf /etc/nginx/

# run nginx, npm install&start
CMD ["sh", "-c", "nginx && npm install && npm start"]
