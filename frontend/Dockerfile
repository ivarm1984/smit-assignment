# Step 1: Build the Angular app
FROM node:18 AS build

WORKDIR /app

# Copy package.json and package-lock.json and install dependencies
COPY package.json package-lock.json ./

RUN npm install

# Copy the rest of the application code
COPY . .

# Build the Angular application (using development mode for ng serve)
RUN npm run build --prod

# Step 2: Serve the app using ng serve (for development)
FROM node:18 AS serve

WORKDIR /app

# Copy only the necessary build artifacts (or the whole app if you prefer)
COPY --from=build /app /app

# Expose port 4200 to access the app in the browser
EXPOSE 4200

# Start the Angular development server
CMD ["npm", "start"]
