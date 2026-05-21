FROM node:18 as build
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod
RUN ls -l /app/dist
RUN ls -l /app/dist/webshop


FROM nginx:alpine
COPY --from=build /app/dist/webshop/browser /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf
