var today = new Date();
var dd = String(today.getDate()).padStart(2, '0');
var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
var yyyy = today.getFullYear();

today = yyyy + '-' + mm + '-' + dd;

Vue.createApp({
    data(){
        return{
            cliente : [],
            prestamos : [],
            tarjetas : [],
            debito : [],
            credito : [],
            hoy : today
        }
    },
    created(){
        axios.get(`http://localhost:8080/api/clients/current`)
        // axios.get(`https://home-banking-mh.herokuapp.com/api/clients/current`)
            .then(response => {
                this.cliente = response.data
                this.prestamos = this.cliente.loans
                this.tarjetas = this.cliente.cards
                this.debito = this.tarjetas.filter(tarjeta => tarjeta.type === "DEBIT" && tarjeta.enable)
                this.credito = this.tarjetas.filter(tarjeta => tarjeta.type === "CREDIT" && tarjeta.enable)
            })

    },
    methods: {
        logout(){
            window.location.href = "/web/index.html";
        },
        crearTarjeta(){
            window.location.href = "/web/create-cards.html";

        },
        eliminar(param){
            axios.patch(`http://localhost:8080/api/clients/current/cards/${param}`)
            // axios.patch(`https://home-banking-mh.herokuapp.com/api/clients/current/cards/${param}`)
                .then(response => {
                    console.log('ok!')
                    window.location.href = "/web/cards.html"
                })
        }

    },
    computed: {

    }
}).mount('#app');