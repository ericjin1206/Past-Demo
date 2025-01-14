#!/usr/bin/env python
# coding: utf-8

# In[ ]:


import streamlit as st
import numpy as np
import faiss
import pickle
from openai import AzureOpenAI

# Azure OpenAI Configuration
AZURE_OPENAI_ENDPOINT = 'https://traversaal-ai-openai-urdu-llmam.openai.azure.com'
AZURE_OPENAI_KEY = '86b6c99a81434e6fa84ab397642ecf91'
AZURE_API_VERSION = "2023-03-15-preview"

# Initialize Azure OpenAI client
client = AzureOpenAI(
    azure_endpoint=AZURE_OPENAI_ENDPOINT,
    api_key=AZURE_OPENAI_KEY,
    api_version=AZURE_API_VERSION
)

# Load FAISS indices and metadata
try:
    text_index = faiss.read_index("/Users/zhengyaojin/Desktop/Capstone_1205/text_index.faiss")
    table_index = faiss.read_index("/Users/zhengyaojin/Desktop/Capstone_1205/table_index.faiss")

    with open("/Users/zhengyaojin/Desktop/Capstone_1205/text_metadata.pkl", "rb") as f:
        text_metadata = pickle.load(f)

    with open("/Users/zhengyaojin/Desktop/Capstone_1205/table_metadata.pkl", "rb") as f:
        table_metadata = pickle.load(f)

except Exception as e:
    st.error(f"Error loading indices or metadata: {e}")

# Function to retrieve relevant content
def retrieve_relevant_content(query, top_k=15):
    """
    Retrieve relevant content (both text and tables) for the query.
    """
    try:
        query_response = client.embeddings.create(
            input=query,
            model="text-embedding-3-small"
        )
        query_embedding = np.array(query_response.data[0].embedding).astype("float32")

        # Search text index
        text_distances, text_indices = text_index.search(np.array([query_embedding]), top_k)
        text_results = [
            f"- **Text Insight**: {text_metadata[idx]['content']}"
            for idx in text_indices[0] if idx != -1
        ]

        # Search table index
        table_distances, table_indices = table_index.search(np.array([query_embedding]), top_k)
        table_results = [
            f"- **Table Data**: {table_metadata[idx]['content']}"
            for idx in table_indices[0] if idx != -1
        ]

        # Combine text and table results
        return "\n\n".join(text_results + table_results)

    except Exception as e:
        return f"Error retrieving content: {e}"

# Function to generate response using RAG
def generate_rag_response(user_query):
    """
    Generate a response using RAG by combining text and table data.
    """
    try:
        context = retrieve_relevant_content(user_query, top_k=15)
        prompt = f"""
        The following context includes text insights and table data extracted from financial documents. Analyze the information and provide a detailed response to the user's query.

        Context:
        {context}

        User Query:
        {user_query}

        Instructions:
        - Combine insights from text and table data.
        - Summarize key points and provide numerical trends from tables.
        - Draw comparisons and explain implications clearly.
        - If possible, provide actionable insights or next steps.
        """
        response = client.chat.completions.create(
            model="gpt-4o-large",
            messages=[
                {"role": "system", "content": "You are a financial data assistant."},
                {"role": "user", "content": prompt}
            ],
            max_tokens=1500,
            temperature=0.5
        )
        return response.choices[0].message.content.strip()

    except Exception as e:
        return f"Error generating response: {e}"

# Streamlit App
# Apply global styles for consistent font and size
st.markdown("""
    <style>
        body {
            font-family: Arial, sans-serif;
            font-size: 16px;
            line-height: 1.6;
        }
        .report-text {
            font-family: Arial, sans-serif;
            font-size: 16px;
            line-height: 1.6;
        }
    </style>
""", unsafe_allow_html=True)

st.title("10-K Analysis and Question Answering")
st.write("""
A system to process and analyze financial documents and answer questions about financial metrics, cost structure, and trends.
""")

# Input Section
st.header("Ask a Question")
user_query = st.text_input("Enter your question about the financial documents:")

# Submit Button Logic
if st.button("Submit"):
    if user_query.strip():
        with st.spinner("Processing your query..."):
            response = generate_rag_response(user_query)

        # Display the response cleanly
        st.subheader("Response")
        if response:
            # Use `st.markdown` for styling the response with the global font
            st.markdown(f"<div class='report-text'>{response}</div>", unsafe_allow_html=True)
        else:
            st.error("No response generated. Please try again.")
    else:
        st.error("Please enter a valid question.")

# Footer
st.write("---")
st.write("Developed for financial analysis and question answering.")

