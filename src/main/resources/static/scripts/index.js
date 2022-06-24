Vue.createApp({
    data(){
        return{
            email : '',
            password : '',
            noRegistrado : false,
            form : true,
            newUseFirstName : '',
            newUserLastName : '',
            newUserEmail : '',
            newUserPassword : '',
            erro : false

        }
    },
    created(){

    },
    methods : {
        singIn(){
            let self = this;
            axios.post('/api/login',`email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                    window.location.href = "/web/accounts.html";
                })
                .catch(error => {
                    this.noRegistrado = true
                  });
        },
        formRegistro(){
            this.form = false
        },
        register(){
            axios.post('/api/clients',`firstName=${this.newUseFirstName}&lastName=${this.newUserLastName}&email=${this.newUserEmail}&password=${this.newUserPassword}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                    this.email = this.newUserEmail
                    this.password = this.newUserPassword
                    this.singIn()
                })
                .catch(error => {
                    this.erro = true
                })
        },
        back(){
            this.form = true
        }

    },
    computed : {

    }
}).mount('#app')
console.log("indexd.js")