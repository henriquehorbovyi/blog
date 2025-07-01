#!/bin/bash

# generate-posts-index.sh
# Script to generate posts index from markdown files

set -e  # Exit on any error

echo "Generating posts index... 📝"

# Create posts directory if it doesn't exist
mkdir -p ./posts

# Create the beginning of JSON file
cat > ./posts/index.json << 'EOF'
{
  "posts": [
EOF

# Check if there are any .md files
if ! ls ./posts/*.md 1> /dev/null 2>&1; then
    echo "⚠️  No markdown files found in posts directory"
    # Close JSON with empty array
    echo '  ]' >> ./posts/index.json
    echo '}' >> ./posts/index.json
    exit 0
fi

# Count total markdown files
total=$(ls ./posts/*.md 2>/dev/null | wc -l)
count=0

echo "📝 Found $total markdown files"

# Loop through all markdown files in posts directory
for file in ./posts/*.md; do
    # Skip if file doesn't exist (shouldn't happen, but just in case)
    [ ! -f "$file" ] && continue

    # Extract filename without extension
    filename=$(basename "$file" .md)

    # Extract title from first line (assuming # Title format)
    title=$(head -n1 "$file" | sed 's/^# *//' | tr -d '\r\n')

    # If no title found or it's still a markdown header, use filename
    if [ -z "$title" ] || [[ "$title" == "#"* ]]; then
        title="$filename"
    fi

    # Get file modification date
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        date=$(stat -f "%Sm" -t "%Y-%m-%d" "$file")
    else
        # Linux
        date=$(date -r "$file" "+%Y-%m-%d")
    fi

    # Increment counter
    count=$((count + 1))

    echo "  📄 Processing: $filename"

    # Add JSON entry
    cat >> ./posts/index.json << EOF
    {
      "id":  "$(basename "$file")_$date",
      "title": "$title",
      "publishedAt": "$date",
      "file": "$(basename "$file")"
EOF

    # Add comma if not the last item
    if [ $count -lt $total ]; then
        echo "    }," >> ./posts/index.json
    else
        echo "    }" >> ./posts/index.json
    fi
done

# Close JSON structure
cat >> ./posts/index.json << 'EOF'
  ]
}
EOF

echo "✅ Generated index with $count posts"
echo "📋 Index file created at: posts/index.json"

# Show the result (optional, for debugging)
if [ "${SHOW_OUTPUT:-false}" = "true" ]; then
    echo "📄 Generated content:"
    cat ./posts/index.json
fi