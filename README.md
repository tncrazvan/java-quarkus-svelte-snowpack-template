# create-svelte

Everything you need to build a Svelte & Quarkus project, powered by [`create-svelte`](https://github.com/sveltejs/kit/tree/master/packages/create-svelte) and [`quarkus`](https://code.quarkus.io/);

## Creating a project

```bash
# create a new project in the current directory
git clone https://github.com/tncrazvan/java-quarkus-svelte-snowpack-template .

# or
npx degit tncrazvan/java-quarkus-svelte-snowpack-template
```


## Developing

Once you've created a project and installed dependencies with `npm install` (or `pnpm install` or `yarn`), start a development compiler/server:

```bash
npm run dev
```

And a Quarkus server:
```bash
mvn quarkus:dev
```

## Building

Svelte apps are built with *adapters*, which optimise your project for deployment to different environments, like [Begin](https://begin.com), [Netlify](https://www.netlify.com), [Vercel](https://vercel.com) and so on. (You can also create your own adapter — instructions TODO.)

By default, `npm run build` will generate a Node app that you can run with `node build`. To use a different adapter, install it and update your `svelte.config.js` accordingly. The following official adapters are available:

* [@sveltejs/adapter-node](https://github.com/sveltejs/kit/tree/master/packages/adapter-node)
* [@sveltejs/adapter-static](https://github.com/sveltejs/kit/tree/master/packages/adapter-static)
* [@sveltejs/adapter-netlify](https://github.com/sveltejs/kit/tree/master/packages/adapter-netlify)
* ...more soon
