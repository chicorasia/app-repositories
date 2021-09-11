# App-Repositories by Chico Rasia

App desenvolvido como projeto final do módulo *Criando um App para Apresentar seu Portfólio do GitHub*, instrutor Ezequiel Messore, bootcamp Inter Android Developer na Digital Innovation One.

Além dos conceitos desenvolvidos nas aulas, o app emprega as seguintes tecnologias e soluções:

- **DataBinding e Listener Bindings**. Adotei essas soluções, sempre que possível, para reduzir o acoplamento do código e ter mais flexibilidade. A visibilidade de componentes é controlada por variáveis do ViewModel.
- **BindingAdapters**. O DataBinding é facilitado por meio dos BindingAdapters, que convertem dados e configuram as views a partir dos dados da entidade. 
- **Boas práticas de uso de estilos, dimensions, etc.** Procurei adotar melhores práticas para a padronização dos componentes visuais nos arquivos XML, tais como o uso de estilos standard do Material Design, extração de atributos @dimen e @string e outras ferramentas, visando a uniformidade, acessibilidade e manutenabilidade do app.
- **Documentação extensiva**. Cada classe traz comentários para explicar o funcionamento e as decisões de projeto.
- **Moshi**. Adotei a biblioteca Moshi para o parseamento de arquivos JSON.  
- **Arquitetura MVVM baseada em Fragments**. Optei por organizar o app em Fragments com ViewModel; a MainActivity tem somente a responsabilidade de manter o NavHostController. Adotei essa solução por ser uma arquitetura mais atual.
- **Android Navigation Component**. Optei por essa solução, em conjunto com os Fragments, porque acredito que dá mais flexibilidade e permite fluxos de navegação mais sofisticados.
- **Safe Args**. Fluxo de dados entre fragmentos facilitado por meio do plugin safe-args.  
- **ListAdapter e ViewHolder**. Adotei melhores práticas como a delegação das responsabilidade de inflar o layout e fazer o binding dos dados a partir da classe ViewHolder, e não nos métodos da classe ListAdapter.
- **Clean Architecture**
- **Versionamento segundo princípios de GitFlow.**

****

## Versões

### V0.1
- Lista os repositórios do usuário no github
- Troca rápida da conta de usuário github
- Solução estética básica

****

## Screenshots

![Screenshot_1](Screenshot_1.png)

![Screenshot_2](Screenshot_2.png)

****

:computer:chicorialabs.com.br/blog

****

🧡 Inter Android Developer bootcamp 2021

