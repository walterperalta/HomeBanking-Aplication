Vue.createApp({
    data(){
        return{
            cliente : [],
            prestamos : [],
            erro : false,
            tipoTarjeta : "",
            colorTarjeta : "",
            debito : [],
            credito : [],
            tarjetas : []
        }
    },
    created(){
        // axios.get(`http://localhost:8080/api/clients/current`)
        axios.get(`https://home-banking-mh.herokuapp.com/api/clients/current`)
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
            // axios.post('http://localhost:8080/api/clients/current/cards',`cardType=${this.tipoTarjeta}&cardColor=${this.colorTarjeta}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            axios.post('https://home-banking-mh.herokuapp.com/api/clients/current/cards',`cardType=${this.tipoTarjeta}&cardColor=${this.colorTarjeta}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                    window.location.href = "/web/cards.html";
                })
                .catch(error => {
                    this.erro = true
                })
        }
    },
    computed: {

    }
}).mount('#app');