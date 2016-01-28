class cwmdt::helloworld {
    exec { 'hello world':
        command => "/bin/echo Hello World >> /tmp/hello.txt"
    }
}
