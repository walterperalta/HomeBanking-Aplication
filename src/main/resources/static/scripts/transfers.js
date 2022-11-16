const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

// myModal.addEventListener('shown.bs.modal', () => myInput.focus())
Vue.createApp({
    data(){
        return{
            cuenta : '',
            origen : '',
            destino : '',
            monto : 0,
            descripcion : '',
            cliente : [],
            cuentas : [],
            create : false,
            mensajeError : '',
            cuentasFiltradas : []
        }
    },
    created(){
        axios.get(`http://localhost:8080/api/clients/current`)
        // axios.get(`https://home-banking-mh.herokuapp.com/api/clients/current`)
            .then(response => {
                this.cliente = response.data
                this.cuentas = this.cliente.accounts.filter(cuenta => cuenta.enable)

            })


    },
    methods: {
        logout(){
            window.location.href = "/web/index.html";
        },
        crearTransaccion(){
            if(this.cuenta==='tercero'){
                this.cuenta == 'VIN'+this.cuenta
            }
            axios.post('http://localhost:8080/api/clients/current/transactions',`amount=${this.monto}&description=${this.descripcion}&origin=${this.origen}&target=${this.destino}`)
            // axios.post('https://home-banking-mh.herokuapp.com/api/clients/current/transactions',`amount=${this.monto}&description=${this.descripcion}&origin=${this.origen}&target=${this.destino}`)
                .then(response => {
                    this.create = true
                })
                .catch(error => {
                    if (this.origen === ''){
                        this.mensajeError = 'No seleccionó una cuenta de origen'
                    } else
                    if (this.destino === ''){
                        this.mensajeError = 'No seleccionó una cuenta de destino'
                    } else
                    if (this.monto === ''){
                        this.mensajeError = 'No indicó el monto'
                    } else
                     if (this.descripcion === ''){
                        this.mensajeError = 'No indicó una descripcion'
                    }
                })
        },
        aceptar(){
            window.location.href = "/web/accounts.html"
        }

    },
    computed: {
        filtrarCuentas(){
            console.log(this.filtrarCuentas)
            this.cuentasFiltradas = this.cuentas.filter(cuenta => cuenta.number !== this.origen);
        }

    }
}).mount('#app');
console.log('transfers.js')