# User Microservice
### Introduction 
This microservice is used to manage users

### Objectives
* CRUD users on a mock DB
* Persist the results of the actions into a dabatase
* Return meaningful data as JSON objects, in order to allow relationships with other entities of the system

### Running SOA-Monolith
1. Navigate to the root directory of the server
2. Run
   ```sh
    $ mvn clean package
    $ mvn spring-boot:run
   ```
3. Perform CRUD operations

### Useful tips to create the infrastructure
   ```sh
    Adicionando um pedido
    curl -H "Content-Type: application/json" -X POST -d '{"idPedido": 1,"idCliente": 1,"item" : {"idProduto":1,"quantidade":1}}' http://localhost:8083/pedidorest/item/adiciona

    Consultado todos os pedidos
    curl http://localhost:8083/pedidorest/

    Efetivando pedido (mudar o status para concluido)
    curl -X PUT http://localhost:8083/pedidorest/pedido/1

    Digamos que nós já temos 5 clientes cadastrados
    // Adicionando um cliente
    curl http://localhost:8082/clienterest/ -H "Content-Type: application/json" -X POST -d '{"id":6,"nome":"Cliente 6","email":"customer6@gmail.com"}'
   ```

### Create an EC2 instance at Amazon
   ```sh
    Login to the console
    Create an EC2 Ubuntu instance 
    Create a key-value pair and download the .pem file
    CHMOD the .pem file to 400
    ssh -i path_pem_file.pem ubuntu@ec2.instance.address
    edit the inboud rules, allow http, https, or add a custom tcp rule, enable the ports you need, I've added 8080 - 8088
   ```

### Installing Docker on a linux distro
   ```sh
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
    sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    sudo apt-get update
    apt-cache policy docker-ce
    sudo apt-get install -y docker-ce
    sudo usermod -aG docker $(whoami)
    su - ${USER}
    id -nG
    sudo su

    // Install sudo
    apt-get update && apt-get install -y sudo 

    // Install apt resources
    apt-get install software-properties-common

    // HTTPS related stuff
    sudo apt-get install apt-transport-https

    // Install curl
    apt-get install curl
   ```

### Installing Jenkins inside of a Docker container
   ```sh
    // https://www.katacoda.com/courses/jenkins/build-docker-images
    // Via Docker - didn't work, the Docker service wouldn't start within the Docker container
    docker run -d -u root --name jenkins \
        -p 8080:8080 -p 50000:50000 \
        -v /root/jenkins:/var/jenkins_home \
        jenkins:1.651.1-alpine
   ```

### Installing Jenkis directly in the host machine
   ```sh
    // Directly on the host machine
    wget -q -O - https://pkg.jenkins.io/debian/jenkins-ci.org.key | sudo apt-key add -
    sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
    sudo apt-get update
    sudo apt-get install jenkins

    // Start Jenkins if needed
    /etc/init.d/jenkins start

    // Install Docker plugin on Jenkins
    https://wiki.jenkins.io/display/JENKINS/Docker+Plugin

    // Install Git plugin
    https://wiki.jenkins.io/display/JENKINS/Git+plugin+2.0+beta+testing

    // Install Github plugin - useful to create triggers (webhooks)
    https://wiki.jenkins.io/display/JENKINS/GitHub+Plugin

    // Install Maven Integration plugin
    https://wiki.jenkins.io/display/JENKINS/Maven+Project+Plugin

    Setup a maven version on the Manage Jenking/Global tools configuration/add maven
    Aggre to the license terms of the jdk at Manage Jenking/Global tools configuration/jdk
    Uncheck automatically install updates
    Install the jdk version of your app
    Update all the plugins
    Commit the Jenkins container and if you restart it, update the plugins again
   ```

### Pull data from a repository with a Github webhook and test it
   ```sh
    // https://techbeacon.com/beginners-guide-kick-starting-your-ci-pipeline-jenkins
    set the repository: git://github.com/DiegoSilva776/DevOpsProj_ms_user.git
    set the branch: master
    add a build trigger: Github hook trigger for GITScm polling
    add a build step: Invoke top-level Maven targets
    add a webhook on github

    // Register the webhook on Github on Integrations & services
    http://server_url:8080/github-webhook/
   ```

### Grant permission to allow Jenkins to run Docker commands in the host machine
   ```sh
    // Do the following to the host of Jenkins
    1 - sudo visudo

    2 - Use NOPASSWD line for all commands:
        jenkins ALL=(ALL) NOPASSWD: ALL

    3 - Put the line after all other lines in the sudoers file

    sudo docker run -p 8082:8082 --name -t diegosilva776/ms_user:0.1
   ```

.