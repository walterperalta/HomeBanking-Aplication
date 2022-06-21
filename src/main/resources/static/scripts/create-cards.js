Vue.createApp({
    data(){
        return{
            cliente : [],
            prestamos : [],
            erro : false,
            tipoTarjeta : "DEBIT",
            colorTarjeta : "TITANIUM"

        }
    },
    created(){
        axios.get(`http://localhost:8080/api/clients/current`)
            .then(response => {
                this.cliente = response.data
                this.prestamos = this.cliente.loans

            })


    },
    methods: {
        logout(){
            window.location.href = "/web/index.html";
        },
        crearTarjeta(){
            axios.post('http://localhost:8080/api/clients/current/cards',`cardType=${this.tipoTarjeta}&color=${this.colorTarjeta}`, {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            })
                .then(response => console.log('Created!'))
                .catch(error => console.log('hubo un error :/'))
        }
    },
    computed: {

    }
}).mount('#app');
console.log('create-cards.js')