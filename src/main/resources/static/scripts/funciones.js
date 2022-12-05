var today = new Date();
var dd = String(today.getDate()).padStart(2, '0');
var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
var yyyy = today.getFullYear();

today = yyyy + '-' + mm + '-' + dd;

const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')


Vue.createApp({
    data(){
        return{
            cliente : [],
            prestamos : [],
            erro : false,
            cuentas : [],
            destino : '',
            create : false,
            // cuentasRestantes: [],
            // cuentaEliminar: '',
            tipoCuenta : '',
            cuentasFiltradas : [],
            tarjetas : [],
            debito : [],
            credito : [],
            hoy : today,
            tipoTarjeta : "",
            colorTarjeta : "",
            debito : [],
            credito : [],
            tarjetas : [],
            tipoPrestamo : [],
            cuotas : [],
            cuotasSeleccionada : '',
            prestamoSeleccionado : '',
            monto : '',
            destino : '',
            cuentas : [],
            montoCuotas : '',
            destino : '',
            modal : '',
            create : false,
            mensajeError : 'hola',
            montoMax: 0,
            cuenta : '',
            origen : '',
            destino : '',
            monto1 : 0,
            descripcion : '',

        }
    },
    created(){
        // axios.get(`http://localhost:8080/api/clients/current`)
        axios.get(`/api/clients/current`)
            .then(response => {
                this.cliente = response.data
                this.prestamos = this.cliente.loans
                this.cuentas = this.cliente.accounts.filter(cuenta => cuenta.enable)
                this.tarjetas = this.cliente.cards
                this.debito = this.tarjetas.filter(tarjeta => tarjeta.type === "DEBIT" && tarjeta.enable)
                this.credito = this.tarjetas.filter(tarjeta => tarjeta.type === "CREDIT" && tarjeta.enable)
            })


    },
    methods: {
        logout(){
            window.location.href = "/web/index.html";
        },
        formatoNumero(number){
            number = number.toFixed(2)
            const exp = /(\d)(?=(\d{3})+(?!\d))/g
            const rep = '$1.'
            let arr = number.toString().split('.')
            arr[0] = arr[0].replace(exp,rep)
            return arr[1] ? arr.join(','): arr[0]
        },        
        aceptar(){
            window.location.href = "/web/accounts.html"
        },


        // Funciones de cuentas.html
 
        crearCuenta(){
            // axios.post('/api/clients/current/accounts',`type=${this.tipoCuenta}`)
            axios.post('/api/clients/current/accounts',`type=${this.tipoCuenta}`)
                .then(response => {
                    console.log("Created!")
                    window.location.href = "/web/accounts.html";
                })
                .catch(error => {
                    console.log("No se puede crear mas cuentas")
                    this.erro = true
                })
        },
        verMovimientos(id){
            window.location.href = `account.html?id=${id}`;
        },
        eliminar(param){
            // axios.patch(`http://localhost:8080/api/accounts/${param}`,`target=${this.destino}`/*,{headers:{'content-type':'application/x-www-form-urlencoded'}}*/)
            axios.patch(`/api/accounts/${param}`,`target=${this.destino}`/*,{headers:{'content-type':'application/x-www-form-urlencoded'}}*/)
            .then(response => {
                console.log('ok!')
                this.create = true
                window.location.href = "/web/accounts.html";
            })

        },
        filtrarCuentas(param){
            this.cuentasFiltradas = this.cuentas.filter(cuenta => cuenta.number != param)
        },
        aceptar(){
            window.location.href = "/web/accounts.html"
        },
        eliminarCuenta(){
            Swal.fire('Any fool can use a computer')
        },

        // Funciones de Tarjetas

        crearTarjeta(){
            window.location.href = "/web/create-cards.html";

        },
        eliminarTarjeta(param){
            // axios.patch(`http://localhost:8080/api/clients/current/cards/${param}`)
            axios.patch(`/api/clients/current/cards/${param}`)
                .then(response => {
                    console.log('ok!')
                    window.location.href = "/web/cards.html"
                })
        },
        crearTarjeta(){
            // axios.post('http://localhost:8080/api/clients/current/cards',`cardType=${this.tipoTarjeta}&cardColor=${this.colorTarjeta}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            axios.post('/api/clients/current/cards',`cardType=${this.tipoTarjeta}&cardColor=${this.colorTarjeta}`)
                .then(response => {
                    window.location.href = "/web/cards.html";
                })
                .catch(error => {
                    this.erro = true
                })
        },

        // Funciones Loan-application

        mostrarCuotas(value){
            // axios.get(`http://localhost:8080/api/loans/${value}`)
            axios.get(`/api/loans/${value}`)
            .then(response => {
                this.tipoPrestamo = response.data
                this.cuotas = this.tipoPrestamo.payments
                this.prestamoSeleccionado = this.tipoPrestamo.name
                this.montoMax = this.formatoNumero(this.tipoPrestamo.maxAmount)
            })
        },
        solicitarPrestamo(){
            // axios.post('http://localhost:8080/api/clients/current/loans',{"id" : this.tipoPrestamo.id,"amount" : this.monto,"payments" : this.cuotasSeleccionada,"target" : this.destino})
            axios.post('/api/clients/current/loans',{"id" : this.tipoPrestamo.id,"amount" : this.monto,"payments" : this.cuotasSeleccionada,"target" : this.destino})
            .then(response => {
                this.create = true
                window.location.href = "/web/accounts.html"
            })
            .catch(error => {
                if (this.cuotasSeleccionada === ''){
                    this.mensajeError = 'No seleccionó la cantidad de cuotas'
                }else
                if (this.prestamoSeleccionado === ''){
                    this.mensajeError = 'No seleccionó un tipo de prestamo'
                }else
                if (this.monto === ''){
                    this.mensajeError = 'No indicó el monto'
                }else
                if (this.destino === ''){
                    this.mensajeError = 'No indicó una cuenta de destino'
                }else {
                    this.mensajeError = 'Alcanzo el limite de prestamo '
                }
            })    
        },

        // Funciones Tranferencias

        crearTransaccion(){
            if(this.cuenta==='tercero'){
                this.cuenta == 'VIN'+this.cuenta
            }
            // axios.post('http://localhost:8080/api/clients/current/transactions',`amount=${this.monto1}&description=${this.descripcion}&origin=${this.origen}&target=${this.destino}`)
            axios.post('/api/clients/current/transactions',`amount=${this.monto1}&description=${this.descripcion}&origin=${this.origen}&target=${this.destino}`)
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
                    if (this.monto1 === ''){
                        this.mensajeError = 'No indicó el monto'
                    } else
                     if (this.descripcion === ''){
                        this.mensajeError = 'No indicó una descripcion'
                    }
                })
        }
    },
    computed: {
        calculoCuota(){
            this.montoCuotas = Math.ceil((this.monto*((this.tipoPrestamo.porcentaje/100)+1))/this.cuotasSeleccionada)
        },
        filtrarCuentas(){
            console.log(this.filtrarCuentas)
            this.cuentasFiltradas = this.cuentas.filter(cuenta => cuenta.number !== this.origen);
        }
        
    }
}).mount('#app');