console.log('hola')

Vue.createApp({
    data(){
        return {
            clientes : 123
        }
    },
    created(){
        // fetch("http://localhost:8080/clients")
        //     .then(response => response.json())
        //         .then(data => this.clientes = data.clients)
        // console.log(this.clientes)
        // console.log("hola")
        // loadData()

    },
    methods : {
        // loadData(){
        //     const axios = require('axios')
        //     axios.get('http://localhost:8080/clients')
        //     .then (response => this.clientes = response.clients)
        //     console.log(this.clientes)
        // }

    },
    computed : {

    }
}).mount('#app')