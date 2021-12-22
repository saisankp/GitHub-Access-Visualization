FROM node:16-alpine
WORKDIR /app
COPY package*.json /app
RUN apk add --update python3 make g++ && rm -rf /var/cache/apk/*
RUN npm install
COPY . /app
CMD ["npm", "start"]