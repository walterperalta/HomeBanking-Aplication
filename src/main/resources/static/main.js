// console.log('holaaa');

Vue.createApp({
    data() {
        return {
            URL : `http://localhost:8080/clients`,
            clientes: [],
            jsonClientes: [],
            nombre: '',
            apellido: '',
            correo: '',
            link: ''
        }
    },
    created() {
        axios.get(this.URL)
            .then(response => {
                this.jsonClientes = response.data
                this.clientes = response.data._embedded.clients
            })
    },
    methods : {  
        agregar_cliente(){
            axios.post(`http://localhost:8080/clients`, {
                firstName: this.nombre,
                lastName: this.apellido,
                email: this.correo,
              })
              .then(function (response) {
                console.log(response);
              })
              .catch(function (error) {
                console.log(error);
              });
        },
        borrarCliente(url){
            axios.delete(url)
        }
        
    },
    computed : {        

    }

}).mount('#app')
